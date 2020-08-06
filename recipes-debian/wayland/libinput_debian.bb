# base recipe: meta/recipes-graphics/wayland/libinput_1.12.6.bb
# base branch: warrior
# base commit: 01784e730a884a9ab7ff073fdb3857b7f597a117

SUMMARY = "Library to handle input devices in Wayland compositors"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/libinput/"
SECTION = "libs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=1f2ea9ebff3a2c6d458faf58492efb63"

inherit debian-package
require recipes-debian/sources/libinput.inc
DEBIAN_QUILT_PATCHES = "${DEBIAN_UNPACK_DIR}/debian/patches"

DEPENDS = "libevdev udev mtdev"

UPSTREAM_CHECK_REGEX = "libinput-(?P<pver>\d+\.\d+\.(?!9\d+)\d+)"
inherit meson pkgconfig lib_package

PACKAGECONFIG ??= ""
PACKAGECONFIG[libwacom] = "-Dlibwacom=true,-Dlibwacom=false,libwacom"
PACKAGECONFIG[gui] = "-Ddebug-gui=true,-Ddebug-gui=false,cairo gtk+3"

UDEVDIR = "`pkg-config --variable=udevdir udev`"

EXTRA_OEMESON += "-Dudev-dir=${UDEVDIR} -Ddocumentation=false -Dtests=false"

# package name changed in 1.8.1 upgrade: make sure package upgrades work
RPROVIDES_${PN} = "libinput"
RREPLACES_${PN} = "libinput"
RCONFLICTS_${PN} = "libinput"
