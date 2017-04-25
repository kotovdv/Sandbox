package com.kotovdv.sandbox.exceptions.jmm

import java.io.OutputStream

class NoListenerAssociatedException(stream: OutputStream) : JMMException("No listener found for [$stream]")