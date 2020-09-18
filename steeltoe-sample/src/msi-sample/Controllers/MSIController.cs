using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;

using Azure.Identity;
using Azure.Security.KeyVault.Secrets;

using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

namespace Microsoft.Azure.SpringCloud.Sample.SimpleApp.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class MSIController : ControllerBase
    {
        private readonly ILogger<MSIController> logger;
        private readonly IConfiguration config;

        private readonly Uri kvUri;

        private SecretClient secretClient;

        private string GetSecretFromKeyVault(string secretName)
        {
            try
            {
                var secret = secretClient.GetSecret(secretName);

                return secret.Value.Value;
            }
            catch (Exception exp)
            {
                Console.WriteLine($"Something went wrong: {exp.Message}");
                return string.Empty;
            }
        }

        private string SetSecretToKeyVault(string secretName, string secretValue)
        {
            try
            {
                var secret = secretClient.SetSecret(secretName, secretValue);

                return secret.Value.Value;
            }
            catch (Exception exp)
            {
                Console.WriteLine($"Something went wrong: {exp.Message}");
                return string.Empty;
            }
        }

        [HttpGet]
        public string GetKeyVaultUri()
        {
            return this.kvUri.ToString();
        }

        public MSIController(IConfiguration config, ILogger<MSIController> logger)
        {
            this.config = config;
            this.logger = logger;
            this.kvUri = new Uri(config["Azure:KeyVault:Uri"]);

            secretClient = new SecretClient(kvUri, new ManagedIdentityCredential());
        }

        [HttpGet("secrets/{secretName}")]
        public string GetSecret(string secretName)
        {
            return GetSecretFromKeyVault(secretName);
        }

        [HttpPut("secrets/{secretName}")]
        public string SetSecret(string secretName, [FromQuery(Name="value")] string secretValue)
        {
            return SetSecretToKeyVault(secretName, secretValue);
        }
    }
}
