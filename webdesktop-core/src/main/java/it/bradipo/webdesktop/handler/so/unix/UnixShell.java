package it.bradipo.webdesktop.handler.so.unix;

import it.bradipo.webdesktop.handler.shell.StringShell;

import java.io.IOException;

class UnixShell extends StringShell {

	public UnixShell() throws IOException {
		super();
	}

	@Override
	protected String getShellCommand() {
		return "/bin/bash";
	}

}
