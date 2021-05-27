using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Steeltoe.Common.Discovery;
using Steeltoe.Discovery;

namespace Microsoft.Azure.SpringCloud.Sample.SolarSystemWeather.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        private const string ProviderAppName = "planet-weather-provider";

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
            logger.LogDebug("Getting weather from solar system planets...");
            using (var client = new HttpClient(discoveryHandler, false))
            {
                var responses = await Task.WhenAll(
                    client.GetAsync($"http://{ProviderAppName}/weatherforecast/Mercury"),
                    client.GetAsync($"http://{ProviderAppName}/weatherforecast/Venus"),
                    client.GetAsync($"http://{ProviderAppName}/weatherforecast/Mars"),
                    client.GetAsync($"http://{ProviderAppName}/weatherforecast/Saturn"));
                logger.LogDebug("Weather provider app returned {0} results", responses.Length);

                var weathers = await Task.WhenAll(from res in responses select res.Content.ReadAsStringAsync());
                logger.LogInformation("Retrieved weather data from {0} planets", weathers.Length);

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
}
