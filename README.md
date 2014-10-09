effective-pom-plugin
====================

Generate effective pom i.e. if your build uses profile and the effective pom with one of the profile active can be different than actual pom, then you might find this plugin helpful. 

Unfortunately `mvn install` just copies the default pom. This is normally okay(or may be marginally frustrating), and can be worked around by using exclusion filter on the installed dependency. But when you have to cross build for scala like publish artifact compiled with different scala version with each artifact having different dependency tree. Then if published pom(s) contain profiles. The consumer of those pom(s) will have tough time resolving right transitive dependencies. 


[TODO: Write more about the usage and give examples of above said issue.]
