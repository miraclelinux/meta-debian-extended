# base recipe: meta-oe/recipes-connectivity/libuv/libuv_1.27.0.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "A multi-platform support library with a focus on asynchronous I/O"
HOMEPAGE = "https://github.com/libuv/libuv"
BUGTRACKER = "https://github.com/libuv/libuv/issues"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a68902a430e32200263d182d44924d47"

inherit autotools

inherit debian-package
require recipes-debian/sources/libuv1.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/libuv1-${PV}"

do_configure() {
    ${S}/autogen.sh || bbnote "${PN} failed to autogen.sh"
    oe_runconf
}

BBCLASSEXTEND = "native"
