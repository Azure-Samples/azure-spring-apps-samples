using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

using System;

namespace Microsoft.Azure.SpringCloud.Sample.EurekaDataProvider.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        private readonly ILogger<WeatherForecastController> logger;

        public WeatherForecastController(ILogger<WeatherForecastController> logger) => this.logger = logger;

        [HttpGet("{planet}")]
        public string Get(string planet)
        {
            logger.LogDebug("Getting weather data for planet {0}", planet);
            if ("Mercury".Equals(planet, StringComparison.OrdinalIgnoreCase))
            {
                return "Very warm";
            }
            else if ("Saturn".Equals(planet, StringComparison.OrdinalIgnoreCase))
            {
                return "A little bit sandy";
            }
            else if ("Pluto".Equals(planet, StringComparison.OrdinalIgnoreCase))
            {
                return "It is not a planet any more";
            }
            else
            {
                return "Unknown";
            }
        }
    }
}
