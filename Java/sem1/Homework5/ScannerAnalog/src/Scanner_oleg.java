import java.io.*;

public class Scanner_oleg {
	private Reader in;
	char[] buffer = new char[1024];
	private int amount = 0; // how many symbols are in buffer
	private int pos = 0; // my current index in buffer

	public Scanner_oleg(File input) throws IOException {
		this.in = new FileReader(input);
	}

	public Scanner_oleg(InputStream input) throws IOException {
		this.in = new InputStreamReader(input);
	}

	public Scanner_oleg(String input) throws IOException {
		this.in = new StringReader(input);
	}

	private boolean filledBuffer() throws IOException {
		if(pos == amount) {
			amount = in.read(buffer);
			pos = 0;
		}
		return amount != -1;
	}

	public boolean hasNext() throws IOException {
		return filledBuffer();
	}

	public String nextLine() throws IOException {
		StringBuilder str = new StringBuilder();
		while(filledBuffer() && buffer[pos] != '\n') { /////// add other 
			str.append(buffer[pos++]);
		}
		pos++;
		return str.toString();
	}

	public String next() throws IOException {
		StringBuilder str = new StringBuilder();
		while(filledBuffer() && Character.isWhitespace(buffer[pos])) {
			pos++;
		}
		while(filledBuffer() && !Character.isWhitespace(buffer[pos])) {
			str.append(buffer[pos++]);
		}
		return str.toString();
	}

	public void close() throws IOException {
		in.close();
	}
}