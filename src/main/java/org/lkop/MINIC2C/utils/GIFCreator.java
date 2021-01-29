package org.lkop.MINIC2C.utils;

import java.io.IOException;


public class GIFCreator {

    public GIFCreator(String filename) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "start dot -Tgif output/"+filename+".dot -o output/"+filename+".gif");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        System.out.println("Exit Code: "+process.waitFor());
    }
}
