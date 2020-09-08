using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Steeltoe.Common.Http.Discovery;
using Steeltoe.Management.Tracing;
using System;

namespace Microsoft.Azure.SpringCloud.Sample.SolarSystemWeather
{
  public class Startup {
    public Startup(IConfiguration configuration) {
      Configuration = configuration;
    }

    public IConfiguration Configuration { get; }

    // This method gets called by the runtime. Use this method to add services to the container.
    public void ConfigureServices(IServiceCollection services) {
      services.AddDistributedTracing(Configuration, builder => builder.UseZipkinWithTraceOptions(services));

      services.AddHttpClient("planetweatherprovider", c =>
        {
          c.BaseAddress = new Uri("http://PLANETWEATHERPROVIDER/weatherforecast/");
        })
        .AddServiceDiscovery()
        .AddTypedClient<Services.IPlanetWeatherProviderService, Services.PlanetWeatherProviderService>();

      services.AddControllers();
    }

    // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
    public void Configure(IApplicationBuilder app, IWebHostEnvironment env) {
      if (env.IsDevelopment()) {
        app.UseDeveloperExceptionPage();
      }

      app.UseRouting();
      app.UseEndpoints(endpoints =>
      {
        endpoints.MapControllers();
      });
    }
  }
}
