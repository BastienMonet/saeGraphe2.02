
mvn compile
mvn exec:java -Dexec.mainClass="org.jgrapht.archetypes.App"

dot -T pdf graph.dot -o graph.pdf