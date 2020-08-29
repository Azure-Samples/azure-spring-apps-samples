using System;

using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Hosting;

using Steeltoe.Extensions.Configuration.ConfigServer;

using Microsoft.Azure.SpringCloud.Client;

namespace Microsoft.Azure.SpringCloud.Sample.PlanetWeatherProvider
{
    public class Program
    {
        public static void Main(string[] args)
        {
            CreateHostBuilder(args).Build().Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>();
                })
                .AddConfigServer()
                .UseAzureSpringCloudService();
    }
}
