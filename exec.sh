
mvn compile
mvn exec:java -Dexec.mainClass="org.jgrapht.archetypes.App"

mvn test -Dtest=AppTest.java

dot -T pdf graph.dot -o graph.pdf