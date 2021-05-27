using Microsoft.AspNetCore.Hosting;
using Microsoft.Azure.SpringCloud.Client;
using Microsoft.Extensions.Hosting;

namespace Microsoft.Azure.SpringCloud.Sample.SolarSystemWeather
{
    public class Program
    {
        public static void Main(string[] args) => CreateHostBuilder(args).Build().Run();

        /// <summary>
        /// Configure steeltoe and Azure Spring Cloud.
        ///
        /// Please ensure you are using the correct version of Microsoft.Azure.SpringCloud.Client:
        /// <list type="bullet">
        ///     <item><term>1.0.0-preview.1</term> for Steeltoe library 2.4.4</item>
        ///     <item><term>2.0.0-preview.1</term> for Steeltoe library 3.0.0</item>
        /// </list>
        ///
        /// Please also notice the sequence of <c>.AddConfigServer()</c> and <c>.UseAzureSpringCloudService()</c>.
        /// If you do not follow the correct sequence mentioned below, your app will not run in Azure.
        ///
        /// For different versions of Microsoft.Azure.SpringCloud.Client:
        /// <list type="bullet">
        ///     <item>
        ///         <term>1.0.0-preview.1</term>
        ///         <c>.UseAzureSpringCloudService()</c> must be added AFTER <c>.AddConfigServer()</c>
        ///     </item>
        ///     <item>
        ///         <term>2.0.0-preview.1</term>
        ///         <c>.UseAzureSpringCloudService()</c> must BEFORE any other Steeltoe configurations
        ///     </item>
        /// </list>
        /// </summary>
        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .UseAzureSpringCloudService()
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>();
                });
    }
}
