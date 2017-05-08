package com.kotovdv.sandbox.exceptions.jvm

import java.io.OutputStream

class NoListenerAssociatedException(stream: OutputStream) : JVMException("No listener found for [$stream]")