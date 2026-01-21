from azure.identity import ManagedIdentityCredential
from azure.keyvault.secrets import SecretClient
import indentityconfig as cfg

credential = ManagedIdentityCredential()
keyVaultUrl = cfg.key_vault["vault_url"]
print("About to connect to: ", keyVaultUrl)
client = SecretClient(keyVaultUrl, credential, logging_enable=True)

def getSecret(name):
    try:
        print("About to get secret: " + name)
        secret = client.get_secret(name)
        return "Successfully got the value of secret {} from Key Vault {}: {}".format(name, keyVaultUrl, secret.value)
    except Exception as e:
        print(e)
        return "Failed to get secret {} from Key Vault {} due to {}".format(name, keyVaultUrl, e.__str__())

def setSecret(name, value):
    try:
        print("About to set secret: ({}, {})".format(name, value))
        secret = client.set_secret(name, value)
        return "Successfully got the value of secret {} from Key Vault {}: {}".format(name, keyVaultUrl, secret.value)
    except Exception as e:
        print(e)
        return "Failed to set secret {} in Key Vault {} due to {}".format(name, keyVaultUrl, e.__str__())