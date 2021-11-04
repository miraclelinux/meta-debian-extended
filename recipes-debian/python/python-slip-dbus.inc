# base recipe: meta-openembedded/meta-python/recipes-devtools/python/python-slip-dbus.inc
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "Convenience functions for dbus services in Python 2.x"
HOMEPAGE = "https://github.com/nphilipp/python-slip"
DESCRIPTION = "\
The Simple Library for Python 2.x packages contain miscellaneous code for \
convenience, extension and workaround purposes. \
\
This package provides slip.dbus.service.Object, which is a dbus.service.Object \
derivative that ends itself after a certain time without being used and/or if \
there are no clients anymore on the message bus, as well as convenience \
functions and decorators for integrating a dbus service with PolicyKit."

SECTION = "devel/python"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=5574c6965ae5f583e55880e397fbb018"

inherit debian-package
require recipes-debian/sources/python-slip.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/python-slip-${PV}"
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_QUILT_PATCHES = ""

do_compile_prepend() {
    sed -e 's/@VERSION@/${PV}/g' setup.py.in > setup.py
}

# http://errors.yoctoproject.org/Errors/Details/184713/
# python-native/python: can't open file 'setup.py': [Errno 2] No such file or directory
CLEANBROKEN = "1"
