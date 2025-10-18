import java.util.*;

enum LogLevel{
    INFO, DEBUG, WARN, ERROR
}

interface Appender {
    void append(String message, LogLevel level);
    String getName();
} 

class Logger{
    private static Logger instance;                               //one single instance, SINGLETON Pattern 
    private List<Appender> appenders = new ArrayList<>();
    private LogLevel currentLevel = LogLevel.INFO;

    private Logger(){
        System.out.println("Logger is initialized");
    }
    public static Logger getInstance(){
        if(instance == null) instance = new Logger();
        return instance;
    }

    public void addAppender(Appender appender){
        appenders.add(appender);
    }

    public void setLevel(LogLevel level){
        currentLevel = level;
    }

    public void log(LogLevel level, String message){
        if(level.ordinal() >= currentLevel.ordinal()){
            for(Appender appender : appenders){
                appender.append(message, level);
            }
        }
    }

    public void getAppenders(){
        for(Appender appender : appenders){
            System.out.println(appender.getName());
        }
    }
}

 class ConsoleAppender implements Appender{
    private String name;
    public ConsoleAppender(String name){
        this.name = name;
    }
    public void append(String message, LogLevel level){
        System.out.println("[" + level + "]" + message + " appended in console");
    }
    public String getName(){
        return name;
    }
 }

 class FileAppender implements Appender{
    private String name;
    public FileAppender(String name){
        this.name = name;
    }
    public void append(String message, LogLevel level){
        System.out.println("[" + level + "]" + message + " appended in file");
    }
    public String getName(){
        return name;
    }
 }



public class logging_framework{
    public static void main(String[] args){
        Logger l = Logger.getInstance();                        //initialized as a singleton
        
        l.addAppender(new ConsoleAppender("console"));
        l.addAppender(new FileAppender("file"));

        l.log(LogLevel.INFO, "System is starting");
        l.log(LogLevel.DEBUG, "Debug the flow");
        l.setLevel(LogLevel.DEBUG);                    // ordinal value set higher 
        l.log(LogLevel.INFO, "HI there");              ////won't be logged because ordinal value is lower than set value;
        
        l.getAppenders();           


    }
}