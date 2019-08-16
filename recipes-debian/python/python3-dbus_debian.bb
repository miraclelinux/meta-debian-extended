# base recipe: meta/recipes-devtools/python/python3-dbus_1.2.8.bb
# base branch: warrior
# base commit: ec9ad3648bd9882fd2004bf5fcb0e0f55e160cc8

SUMMARY = "Python bindings for the DBus inter-process communication system"
SECTION = "devel/python"
HOMEPAGE = "http://www.freedesktop.org/Software/dbus"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=b03240518994df6d8c974675675e5ca4"
DEPENDS = "expat dbus dbus-glib virtual/libintl"

inherit debian-package
require recipes-debian/sources/dbus-python.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/dbus-python-${PV}"

inherit distutils3-base autotools pkgconfig

# documentation needs python3-sphinx, which is not in oe-core or meta-python for now
# change to use PACKAGECONFIG when python3-sphinx is added to oe-core
EXTRA_OECONF += "--disable-documentation"


RDEPENDS_${PN} = "python3-io python3-logging python3-stringold python3-threading python3-xml"

FILES_${PN}-dev += "${libdir}/pkgconfig"
