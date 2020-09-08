using System.Collections.Generic;
using System.Threading.Tasks;

namespace Microsoft.Azure.SpringCloud.Sample.WeatherApp.Services {
	public interface ISolarSystemService {
		Task<IEnumerable<KeyValuePair<string, string>>> GetPlanetWeather();
	}
}