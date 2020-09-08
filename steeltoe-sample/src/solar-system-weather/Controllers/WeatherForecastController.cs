using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

using Steeltoe.Common.Discovery;

using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace Microsoft.Azure.SpringCloud.Sample.SolarSystemWeather.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        private readonly ILogger<WeatherForecastController> _logger;
        private readonly Services.IPlanetWeatherProviderService _planetWeatherProviderService;

        public WeatherForecastController(ILogger<WeatherForecastController> logger, Services.IPlanetWeatherProviderService planetWeatherProviderService)
        {
            _logger = logger;
           _planetWeatherProviderService = planetWeatherProviderService;
        }

        [HttpGet]
        public async Task<IEnumerable<KeyValuePair<string, string>>> Get()
        {
            _logger.LogDebug("Getting weather from solar system planets...");

            var weathers = await _planetWeatherProviderService.GetPlanetForecasts();

            _logger.LogInformation("Retrieved weather data from {0} planets", weathers.Length);

            return new[]
            {
                new KeyValuePair<string, string>("Mercury", weathers[0]),
                new KeyValuePair<string, string>("Venus", weathers[1]),
                new KeyValuePair<string, string>("Mars", weathers[2]),
                new KeyValuePair<string, string>("Saturn", weathers[3]),
            };
        }
    }
}
