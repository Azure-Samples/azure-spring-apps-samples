using Microsoft.AspNetCore.Hosting;
using Microsoft.Azure.SpringCloud.Client;
using Microsoft.Extensions.Hosting;

namespace Microsoft.Azure.SpringCloud.Sample.EurekaDataConsumer
{
    public class Program
    {
        public static void Main(string[] args) => CreateHostBuilder(args).Build().Run();

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>();
                })
                .UseAzureSpringCloudService();
    }
}
