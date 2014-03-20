package it.bradipo.webdesktop.handler.so.windows;

import it.bradipo.webdesktop.handler.shell.StringShell;

import java.io.IOException;

class WindowsShell extends StringShell {

	public WindowsShell() throws IOException {
		super();
	}

	@Override
	protected String getShellCommand() {
		return "cmd.exe";
	}

}
