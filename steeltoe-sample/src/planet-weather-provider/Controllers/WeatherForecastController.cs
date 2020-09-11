using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

namespace Microsoft.Azure.SpringCloud.Sample.PlanetWeatherProvider.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        private readonly ILogger<WeatherForecastController> logger;
        private readonly IConfiguration config;

        public WeatherForecastController(IConfiguration config, ILogger<WeatherForecastController> logger)
        {
            this.config = config;
            this.logger = logger;
        }

        [HttpGet("{planet}")]
        public string Get(string planet)
        {
            logger.LogDebug("Getting weather data for planet {0}", planet);

            var weatherConfigKey = $"{planet}Weather";
            logger.LogDebug("Getting weather forcast data ({0}) from config server...", weatherConfigKey);

            var weatherFromConfig = config[weatherConfigKey];

            logger.LogInformation("Weather forcast data retrieved from config: {0}", weatherFromConfig);
            return string.IsNullOrEmpty(weatherFromConfig) ? "Unknown" : weatherFromConfig;
        }
    }
}
