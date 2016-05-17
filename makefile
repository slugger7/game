default:
	javac Battleship.java Mail.java HttpServer.java

run:
	java HttpServer

clean:
	rm *.class