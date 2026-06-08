const { SecretClient } = require("@azure/keyvault-secrets");
const { ManagedIdentityCredential } = require("@azure/identity");
const util = require('util');

const credential = new ManagedIdentityCredential()

const url = process.env.AZURE_KEYVAULT_URI;
console.log("About to connect to: ", url);
const client = new SecretClient(url, credential);

async function getSecret(name) {
    console.log("About to get secret: " + name);
    try {
        var secret = await client.getSecret(name);
        return util.format("Successfully got the value of secret %s from Key Vault %s: %s",
                    name, url, secret.value);
    } catch (err) {
        console.log(err)
        return util.format("Failed to get secret %s from Key Vault %s due to %s", name,
                    url, err.message);
    }
}

async function setSecret(name, value) {
    console.log(util.format("About to set secret: (%s, %s)"), name, value);

    try {
        await client.setSecret(name, value);
        return util.format("Successfully set secret %s in Key Vault %s", name, url);
    } catch (err) {
        console.log(err)
        return util.format("Failed to set secret %s in Key Vault %s due to %s", name,
                    url, err.message);
    }
}

module.exports = {getSecret, setSecret};