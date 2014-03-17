package it.bradipo.webdesktop.handler.so.windows;

import java.io.IOException;

import it.bradipo.webdesktop.handler.shell.StringShell;

class WindowsShell extends StringShell {

	public WindowsShell() throws IOException {
		super();
	}

	@Override
	protected String getShellCommand() {
		return "cmd.exe";
	}

}
