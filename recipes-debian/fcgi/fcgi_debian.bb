# base recipe: meta-openembedded/meta-webserver/recipes-support/fcgi/fcgi_git.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

DESCRIPTION = "FastCGI is a protocol for interfacing interactive programs with a web server."
HOMEPAGE = "http://www.fastcgi.com"
LICENSE = "OML"
LIC_FILES_CHKSUM = "file://LICENSE.TERMS;md5=e3aacac3a647af6e7e31f181cda0a06a"
BNP = "libfcgi"

inherit autotools

inherit debian-package
require recipes-debian/sources/libfcgi.inc

PARALLEL_MAKE = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/libfcgi-${PV}.orig"

do_configure_prepend() {
    touch ${S}/NEWS ${S}/AUTHORS ${S}/ChangeLog
}
