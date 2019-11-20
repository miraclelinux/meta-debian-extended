SUMMARY = "ping-like program for http-requests"
DESCRIPTION = " \
Httping is like 'ping' but for http-requests. Give it an url, and it'll \
show you how long it takes to connect, send a request and retrieve the \
reply (only the headers). Be aware that the transmission across the network \
also takes time. \
"

inherit debian-package
require recipes-debian/sources/httping.inc
DEBIAN_QUILT_PATCHES = ""

LICENSE = "AGPL-3.0-with-OpenSSL-exception"
LIC_FILES_CHKSUM = "file://license.txt;md5=c40ac4927244d97b4add90e0774e3948"

DEPENDS = "fftw gettext-native ncurses openssl"

do_install() {
    oe_runmake DESTDIR=${D} install
}
