package Utils;

import Engine.Engine;
import org.jetbrains.annotations.NotNull;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

public class UpdaterHandler {
    private final ScheduledFuture<?> updater;

    public UpdaterHandler(@NotNull ScheduledFuture<?> updater){
        this.updater=updater;
    }

    public void stop(){
        updater.cancel(true);
    }
}
