SUMMARY = "Python bindings for libcurl"
HOMEPAGE = "http://pycurl.sourceforge.net/"
SECTION = "devel/python"
LICENSE = "LGPLv2.1+ | MIT"
LIC_FILES_CHKSUM = "file://README.rst;beginline=169;endline=185;md5=67de38adf8bf75f568103dae807bc285 \
                    file://COPYING-LGPL;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING-MIT;md5=7ba8b7264d9ea6da77feda54688adbd7"

inherit debian-package
require recipes-debian/sources/pycurl.inc

DEPENDS = "curl ${PYTHON_PN}"
RDEPENDS_${PN} = "${PYTHON_PN}-core curl"
SRCNAME = "pycurl"

SRC_URI += "file://no-static-link.patch"

DEBIAN_UNPACK_DIR = "${WORKDIR}/${SRCNAME}-${PV}"

BBCLASSEXTEND = "native"

# Ensure the docstrings are generated as make clean will remove them
do_compile_prepend() {
	${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} setup.py docstrings
}

do_install_append() {
	rm -rf ${D}${datadir}/share
}
