package com.linux;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "safenow-extended-jwt", mixinStandardHelpOptions = true)
public class SafeNowExtendedJwtCreatorCommand implements Runnable {

    @Parameters(paramLabel = "<name>", defaultValue = "picocli", description = "Your name.")
    String name;

    @Override
    public void run() {
        System.out.printf("Hello %s, go go commando!\n", name);
    }

}
