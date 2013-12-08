package org.ovirt.engine.ui.uicompat;

public class Task {
    public static Task create(ITaskTarget target, Object state)
    {
        return new Task(target, state);
    }

    private ITaskTarget target;
    private TaskContext context;

    private Task(ITaskTarget target, Object state)
    {
        this.target = target;

        context = new TaskContext(Dispatcher.CurrentDispatcher, state);
    }

    public void Run()
    {
        target.run(context);
    }

}
