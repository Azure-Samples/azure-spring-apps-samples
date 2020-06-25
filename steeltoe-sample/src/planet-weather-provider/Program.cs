using System;

using Microsoft.AspNetCore.Hosting;
using Microsoft.Azure.SpringCloud.Client;
using Microsoft.Extensions.Hosting;

using Steeltoe.Extensions.Configuration.ConfigServer;

namespace Microsoft.Azure.SpringCloud.Sample.PlanetWeatherProvider
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var oldURL = Environment.GetEnvironmentVariable("ASCSVCRT_MANAGEMENT__TRACING__EXPORTER__ZIPKIN__ENDPOINT");
            Console.WriteLine("oldURL = {0}", oldURL);
            Environment.SetEnvironmentVariable("ASCSVCRT_MANAGEMENT__TRACING__EXPORTER__ZIPKIN__ENDPOINT", oldURL + "api/v2/spans");
            var newURL = Environment.GetEnvironmentVariable("ASCSVCRT_MANAGEMENT__TRACING__EXPORTER__ZIPKIN__ENDPOINT");
            Console.WriteLine("newValue = {0}", newURL);

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
