# base recipe: meta-oe/recipes-support/libgpiod/libgpiod_1.3.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8
require libgpiod.inc

inherit debian-package
require recipes-debian/sources/libgpiod.inc
DEBIAN_QUILT_PATCHES = ""

DEPENDS += "autoconf-archive-native"

PACKAGECONFIG[cxx] = "--enable-bindings-cxx,--disable-bindings-cxx"
PACKAGECONFIG[tests] = "--enable-tests --enable-install-tests,--disable-tests --disable-install-tests,kmod udev"
PACKAGECONFIG_CONFARGS_remove = "--disable-install-tests"

PACKAGECONFIG[python3] = "--enable-bindings-python,--disable-bindings-python,python3"
inherit ${@bb.utils.contains('PACKAGECONFIG', 'python3', 'python3native', '', d)}

PACKAGES =+ "${PN}-python"
FILES_${PN}-python = "${PYTHON_SITEPACKAGES_DIR}"
RRECOMMENDS_PYTHON = "${@bb.utils.contains('PACKAGECONFIG', 'python3', '${PN}-python', '',d)}"
RRECOMMENDS_${PN}-python += "${RRECOMMENDS_PYTHON}"

