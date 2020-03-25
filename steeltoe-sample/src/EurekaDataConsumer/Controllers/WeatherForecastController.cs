using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

using Steeltoe.Common.Discovery;

using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace Microsoft.Azure.SpringCloud.Sample.EurekaDataConsumer.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        private readonly ILogger<WeatherForecastController> logger;
        private readonly DiscoveryHttpClientHandler discoveryHandler;

        public WeatherForecastController(IDiscoveryClient discovery, ILogger<WeatherForecastController> logger)
        {
            discoveryHandler = new DiscoveryHttpClientHandler(discovery, logger);
            this.logger = logger;
        }

        [HttpGet]
        public async Task<IEnumerable<KeyValuePair<string, string>>> Get()
        {
            logger.LogDebug("Getting weather from two planets: mercury, saturn...");
            using (var client = new HttpClient(discoveryHandler, false))
            {
                var responses = await Task.WhenAll(
                    client.GetAsync("http://planet-weather-provider/weatherforecast/mercury"),
                    client.GetAsync("http://planet-weather-provider/weatherforecast/saturn"));
                logger.LogDebug("Weather provider app returned {0} results", responses.Length);

                var weathers = await Task.WhenAll(from res in responses select res.Content.ReadAsStringAsync());
                logger.LogInformation("Retrieved {0} weather data from planets mercury and saturn", weathers.Length);

                return new[]
                {
                    new KeyValuePair<string, string>("Mercury", weathers[0]),
                    new KeyValuePair<string, string>("Saturn", weathers[1]),
                };
            }
        }
    }
}
