using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

using System.Collections.Generic;

namespace Microsoft.Azure.SpringCloud.Sample.ConfigServer.Controllers
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

        [HttpGet]
        public IEnumerable<KeyValuePair<string, string>> Get()
        {
            logger.LogDebug("Getting weather forcast data from config server...");

            var marsWeather = config["MarsWeather"];
            var venusWeather = config["VenusWeather"];

            logger.LogInformation("Weather forcast data retrieved: {0} {1}", marsWeather, venusWeather);

            yield return new KeyValuePair<string, string>("Mars", marsWeather);
            yield return new KeyValuePair<string, string>("Venus", venusWeather);
        }
    }
}
