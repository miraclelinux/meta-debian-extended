#
# poky rev: 753e2a0ede4449917c75353b57f13bbafe70fac8
# file: https://git.yoctoproject.org/cgit/cgit.cgi/poky/tree/meta/recipes-devtools/apt/apt.inc?id=753e2a0ede4449917c75353b57f13bbafe70fac8
#
SUMMARY = "Advanced front-end for dpkg"
LICENSE = "GPLv2.0+"
SECTION = "base"
LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit debian-package
require recipes-debian/sources/apt.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/apt/apt:${COREBASE}/meta/recipes-devtools/apt/files:"
SRC_URI += "file://no-nls-dpkg.patch \
        file://fix-gcc-4.6-null-not-defined.patch \
        file://truncate-filename.patch \
        file://nodoc.patch \
        file://disable-test.patch \
        file://0001-apt-1.2.12-Fix-musl-build.patch \
        file://0001-Include-array.h-for-std-array.patch \
	"

inherit gettext useradd

# apt's cmake requires perl 
inherit perlnative cmake

OECMAKE_CXX_FLAGS += " -fvisibility-inlines-hidden -Wall -Wextra -Wcast-align -Wlogical-op -Wredundant-decls -Wmissing-declarations -Wunsafe-loop-optimizations -Wctor-dtor-privacy -Wdisabled-optimization -Winit-self -Wmissing-include-dirs -Wnoexcept -Wsign-promo -Wundef -Wdouble-promotion -std=gnu++11"

OECMAKE_CXX_LINK_FLAGS += " -Wl,--as-needed"

# Set COMMON_ARCH variable here instead of modify use-host.patch from poky.
EXTRA_OECMAKE += " -DDPKG_DATADIR=/usr/share/dpkg -DCOMMON_ARCH=${TARGET_ARCH}"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --no-create-home --home-dir /nonexistent --shell /bin/false --user-group _apt"

