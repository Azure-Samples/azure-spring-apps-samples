using System.Threading.Tasks;

namespace Microsoft.Azure.SpringCloud.Sample.SolarSystemWeather.Services {
	public interface IPlanetWeatherProviderService {
		Task<string[]> GetPlanetForecasts();
	}
}