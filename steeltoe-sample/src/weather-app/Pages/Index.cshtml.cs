using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

namespace Microsoft.Azure.SpringCloud.Sample.WeatherApp.Pages
{
    public class IndexModel : PageModel
    {
        private readonly ILogger<IndexModel> _logger;
        private readonly IConfiguration _config;
        private readonly Services.ISolarSystemService _solarSystemService;


        public IEnumerable<KeyValuePair<string, string>> CurrentWeather;

        public IndexModel(IConfiguration config,Services.ISolarSystemService solarSystemService, ILogger<IndexModel> logger)
        {
            _logger = logger;
            _config = config;
            _solarSystemService = solarSystemService;
        }

        public async Task OnGet()
        {
            CurrentWeather = await _solarSystemService.GetPlanetWeather();
        }
    }
}
