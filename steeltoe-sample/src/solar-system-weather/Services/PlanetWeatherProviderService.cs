using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace Microsoft.Azure.SpringCloud.Sample.SolarSystemWeather.Services {
	public class PlanetWeatherProviderService : IPlanetWeatherProviderService {
		private ILogger<PlanetWeatherProviderService> _logger;
		private readonly HttpClient _httpClient;

		public PlanetWeatherProviderService(HttpClient httpClient, ILoggerFactory logFactory) {
			_logger = logFactory.CreateLogger<PlanetWeatherProviderService>();
			_httpClient = httpClient;
		}

		public async Task<string[]> GetPlanetForecasts() {
			var responses = await Task.WhenAll(
				_httpClient.GetAsync($"Mercury"),
				_httpClient.GetAsync($"Venus"),
				_httpClient.GetAsync($"Mars"),
				_httpClient.GetAsync($"Saturn"));

			_logger.LogDebug("Weather provider app returned {0} results", responses.Length);

			var ret = await Task.WhenAll(from res in responses select res.Content.ReadAsStringAsync());
			return ret;
		}
	}
}
