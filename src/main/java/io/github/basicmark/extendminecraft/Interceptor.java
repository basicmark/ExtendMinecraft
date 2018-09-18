package io.github.basicmark.extendminecraft;

public class Interceptor {
	protected ExtendMinecraft plugin;

    public Interceptor(ExtendMinecraft plugin) {
        this.plugin = plugin;
    }

    public ExtendMinecraft getPlugin() {
        return plugin;
    }
}
