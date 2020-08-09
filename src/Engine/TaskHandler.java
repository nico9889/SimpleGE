package Engine;

import Engine.Engine.Task;

public class TaskHandler {
    private Task task;

    public TaskHandler(Task task){
        this.task = task;
    }

    public void stop(){
        this.task.stop();
    }

    public void start(){
        if(task.isStopped())
            task = this.task.resume();
    }
}
