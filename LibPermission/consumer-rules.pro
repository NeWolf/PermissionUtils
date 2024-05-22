-keep class com.newolf.**{
    public <fields>;
    public <methods>;
    }
-keep class com.newolf.*$*{
    public <fields>;
    public <methods>;
    }

-keep interface com.newolf.**{
     public <fields>;
     public <methods>;
    }
-keep interface com.newolf.*$*{
    public <fields>;
    public <methods>;
    }
-dontwarn com.newolf.**
-dontwarn com.newolf.**
# keep所有类的protected成员
-keep public class * {
      public protected *;
}