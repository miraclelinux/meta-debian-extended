# base recipe: meta/recipes-bsp/grub/grub2.inc
# base branch: warrior
# base commit: c8c383d958807b991e31d22f612ba2a81a3860a4

SUMMARY = "GRUB2 is the next-generation GRand Unified Bootloader"

DESCRIPTION = "GRUB2 is the next generaion of a GPLed bootloader \
intended to unify bootloading across x86 operating systems. In \
addition to loading the Linux kernel, it implements the Multiboot \
standard, which allows for flexible loading of multiple boot images."

HOMEPAGE = "http://www.gnu.org/software/grub/"
SECTION = "bootloaders"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

inherit debian-package
require recipes-debian/sources/grub2.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/grub-${REPACK_PV}"

FILESPATH_append = ":${COREBASE}/meta/recipes-bsp/grub/files"
SRC_URI += "file://0001-Disable-mfpmath-sse-as-well-when-SSE-is-disabled.patch \
           file://0001-autogen.sh-exclude-.pc-from-po-POTFILES.in.patch \
	   file://0001-grub-module-explicitly-keeps-symbole-.module_license.patch \
           file://0001-grub.d-10_linux.in-add-oe-s-kernel-name.patch \
           file://0001-fix.build.with.gcc-7.patch-Apply-fix.build.with.gcc-.patch \
           file://0001-Disable-address-of-packed-member-warnings-with-gcc9.patch \
"

DEPENDS = "flex-native bison-native bison pkgconfig"

COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*)-(linux.*|freebsd.*)'
COMPATIBLE_HOST_armv7a = 'null'
COMPATIBLE_HOST_armv7ve = 'null'

# configure.ac has code to set this automagically from the target tuple
# but the OE freeform one (core2-foo-bar-linux) don't work with that.

GRUBPLATFORM_arm = "efi"
GRUBPLATFORM_aarch64 = "efi"
GRUBPLATFORM ??= "pc"

inherit autotools gettext texinfo

EXTRA_OECONF = "--with-platform=${GRUBPLATFORM} \
                --disable-grub-mkfont \
                --program-prefix="" \
                --enable-liblzma=no \
                --enable-libzfs=no \
                --enable-largefile \
"

PACKAGECONFIG ??= ""
PACKAGECONFIG[grub-mount] = "--enable-grub-mount,--disable-grub-mount,fuse"
PACKAGECONFIG[device-mapper] = "--enable-device-mapper,--disable-device-mapper,libdevmapper"

# grub2 creates its own set of -nostdinc / -isystem / -ffreestanding CFLAGS and
# OE's default BUILD_CFLAGS (assigned to CFLAGS for native builds) etc, conflict
# with that. Note that since BUILD_CFLAGS etc are not used by grub2 target
# builds, it's safe to clear them unconditionally for both target and native.
BUILD_CPPFLAGS = ""
BUILD_CFLAGS = ""
BUILD_CXXFLAGS = ""
BUILD_LDFLAGS = ""

do_configure_prepend() {
	# The grub2 configure script uses variables such as TARGET_CFLAGS etc
	# for its own purposes. Remove the OE versions from the environment to
	# avoid conflicts.
	unset TARGET_CPPFLAGS TARGET_CFLAGS TARGET_CXXFLAGS TARGET_LDFLAGS
	( cd ${S}
	${S}/autogen.sh )
}

RDEPENDS_${PN}_class-native = ""
