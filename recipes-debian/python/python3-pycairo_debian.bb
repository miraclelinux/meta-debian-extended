# From: poky/meta/recipes-devtools/python/python3-pycairo_1.15.6.bb
# Rev: 7aa9be36dbb911d860990030188bea8e29a8375d
SUMMARY = "Python bindings for the Cairo canvas library"
HOMEPAGE = "http://cairographics.org/pycairo"
BUGTRACKER = "http://bugs.freedesktop.org"
SECTION = "python-devel"
LICENSE = "LGPLv2.1 & MPLv1.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=f2e071ab72978431b294a0d696327421 \
                    file://COPYING-LGPL-2.1;md5=fad9b3332be894bab9bc501572864b29 \
                    file://COPYING-MPL-1.1;md5=bfe1f75d606912a4111c90743d6c7325"

# cairo >= 1.14
DEPENDS = "cairo"

inherit debian-package
require recipes-debian/sources/pycairo.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/pycairo-${PV}"

inherit setuptools3 pkgconfig

CFLAGS += "-fPIC"

BBCLASSEXTEND = "native"

do_install_append() {
    install -d ${D}${includedir}/pycairo/
    install -m 0644 ${D}${datadir}/include/pycairo/py3cairo.h ${D}${includedir}/pycairo/
}
FILES_${PN} += "${datadir}/include/pycairo/py3cairo.h"
FILES_${PN} += "${datadir}/lib/pkgconfig/py3cairo.pc"