package it.bradipo.webdesktop.handler.so.unix;

import java.io.IOException;

import it.bradipo.webdesktop.handler.shell.StringShell;

class UnixShell extends StringShell {

	public UnixShell() throws IOException {
		super();
	}

	@Override
	protected String getShellCommand() {
		return "/bin/bash";
	}

}
