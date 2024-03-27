package org.springframework.batch.samples.football;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.samples.football.internal.GameFieldSetMapper;
import org.springframework.batch.samples.football.internal.JdbcGameDao;
import org.springframework.batch.samples.football.internal.JdbcPlayerDao;
import org.springframework.batch.samples.football.internal.JdbcPlayerSummaryDao;
import org.springframework.batch.samples.football.internal.PlayerFieldSetMapper;
import org.springframework.batch.samples.football.internal.PlayerItemWriter;
import org.springframework.batch.samples.football.internal.PlayerSummaryMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;

@Configuration
@EnableBatchProcessing
public class FootballJobConfiguration {

	// step 1 configuration

	@Bean
	public FlatFileItemReader<Player> playerFileItemReader() {
		return new FlatFileItemReaderBuilder<Player>().name("playerFileItemReader")
			.resource(new ClassPathResource("org/springframework/batch/samples/football/data/player-small1.csv"))
			.delimited()
			.names("ID", "lastName", "firstName", "position", "birthYear", "debutYear")
			.fieldSetMapper(new PlayerFieldSetMapper())
			.build();
	}

	@Bean
	public PlayerItemWriter playerWriter() {
		PlayerItemWriter playerItemWriter = new PlayerItemWriter();
		JdbcPlayerDao playerDao = new JdbcPlayerDao();
		playerDao.setDataSource(dataSource());
		playerItemWriter.setPlayerDao(playerDao);
		return playerItemWriter;
	}

	@Bean
	public Step playerLoad(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		return new StepBuilder("playerLoad", jobRepository).<Player, Player>chunk(2, transactionManager)
			.reader(playerFileItemReader())
			.writer(playerWriter())
			.build();
	}

	// step 2 configuration

	@Bean
	public FlatFileItemReader<Game> gameFileItemReader() {
		return new FlatFileItemReaderBuilder<Game>().name("gameFileItemReader")
			.resource(new ClassPathResource("org/springframework/batch/samples/football/data/games-small.csv"))
			.delimited()
			.names("id", "year", "team", "week", "opponent", "completes", "attempts", "passingYards", "passingTd",
					"interceptions", "rushes", "rushYards", "receptions", "receptionYards", "totalTd")
			.fieldSetMapper(new GameFieldSetMapper())
			.build();
	}

	@Bean
	public JdbcGameDao gameWriter() {
		JdbcGameDao jdbcGameDao = new JdbcGameDao();
		jdbcGameDao.setDataSource(dataSource());
		return jdbcGameDao;
	}

	@Bean
	public Step gameLoad(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		return new StepBuilder("gameLoad", jobRepository).<Game, Game>chunk(2, transactionManager)
			.reader(gameFileItemReader())
			.writer(gameWriter())
			.build();
	}

	// step 3 configuration

	@Bean
	public JdbcCursorItemReader<PlayerSummary> playerSummarizationSource() {
		String sql = """
				SELECT GAMES.player_id, GAMES.year_no, SUM(COMPLETES),
				SUM(ATTEMPTS), SUM(PASSING_YARDS), SUM(PASSING_TD),
				SUM(INTERCEPTIONS), SUM(RUSHES), SUM(RUSH_YARDS),
				SUM(RECEPTIONS), SUM(RECEPTIONS_YARDS), SUM(TOTAL_TD)
				from GAMES, PLAYERS where PLAYERS.player_id =
				GAMES.player_id group by GAMES.player_id, GAMES.year_no
				""";
		return new JdbcCursorItemReaderBuilder<PlayerSummary>().name("playerSummarizationSource")
			.ignoreWarnings(true)
			.sql(sql)
			.dataSource(dataSource())
			.rowMapper(new PlayerSummaryMapper())
			.build();
	}

	@Bean
	public JdbcPlayerSummaryDao summaryWriter() {
		JdbcPlayerSummaryDao jdbcPlayerSummaryDao = new JdbcPlayerSummaryDao();
		jdbcPlayerSummaryDao.setDataSource(dataSource());
		return jdbcPlayerSummaryDao;
	}

	@Bean
	public Step summarizationStep(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
		return new StepBuilder("summarizationStep", jobRepository)
			.<PlayerSummary, PlayerSummary>chunk(2, transactionManager)
			.reader(playerSummarizationSource())
			.writer(summaryWriter())
			.build();
	}

	// job configuration

	@Bean
	public Job job(JobRepository jobRepository, @Qualifier("playerLoad")Step playerLoad, @Qualifier("gameLoad")Step gameLoad, @Qualifier("summarizationStep")Step summarizationStep) {
		return new JobBuilder("footballJob", jobRepository).start(playerLoad)
			.next(gameLoad)
			.next(summarizationStep)
			.build();
	}

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
			.addScript("/org/springframework/batch/core/schema-drop-hsqldb.sql")
			.addScript("/org/springframework/batch/core/schema-hsqldb.sql")
			.addScript("/org/springframework/batch/samples/football/sql/schema.sql")
			.build();
	}

	@Bean
	public JdbcTransactionManager transactionManager(DataSource dataSource) {
		return new JdbcTransactionManager(dataSource);
	}

}