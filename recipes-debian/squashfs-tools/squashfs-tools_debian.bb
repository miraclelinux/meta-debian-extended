# base recipe: meta/recipes-devtools/squashfs-tools/squashfs-tools_git.bb
# base branch: warrior
# base commit: b2b48f0253dd5b1a1d1ffd0071b6528c0d90a049

# Note, we can probably remove the lzma option as it has be replaced with xz,
# and I don't think the kernel supports it any more.
SUMMARY = "Tools for manipulating SquashFS filesystems"
SECTION = "base"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/${BPN}/${BPN}"

SRC_URI += " \
           file://squashfs-tools-4.3-sysmacros.patch;striplevel=2 \
           file://fix-compat.patch \
"

DEBIAN_UNPACK_DIR = "${WORKDIR}/squashfs${PV}"
S = "${DEBIAN_UNPACK_DIR}/${BPN}"

EXTRA_OEMAKE = "${PACKAGECONFIG_CONFARGS}"

PACKAGECONFIG ??= "gzip xz lzo lz4 lzma xattr"
PACKAGECONFIG[gzip] = "GZIP_SUPPORT=1,GZIP_SUPPORT=0,zlib"
PACKAGECONFIG[xz] = "XZ_SUPPORT=1,XZ_SUPPORT=0,xz"
PACKAGECONFIG[lzo] = "LZO_SUPPORT=1,LZO_SUPPORT=0,lzo"
PACKAGECONFIG[lz4] = "LZ4_SUPPORT=1,LZ4_SUPPORT=0,lz4"
PACKAGECONFIG[lzma] = "LZMA_XZ_SUPPORT=1,LZMA_XZ_SUPPORT=0,xz"
PACKAGECONFIG[xattr] = "XATTR_SUPPORT=1,XATTR_SUPPORT=0,attr"

RDEPENDS_${PN} += "libgcc"

do_compile() {
	oe_runmake mksquashfs unsquashfs
}

do_install () {
	install -d ${D}${sbindir}
	install -m 0755 mksquashfs ${D}${sbindir}/
	install -m 0755 unsquashfs ${D}${sbindir}/
}

ARM_INSTRUCTION_SET_armv4 = "arm"
ARM_INSTRUCTION_SET_armv5 = "arm"
ARM_INSTRUCTION_SET_armv6 = "arm"

BBCLASSEXTEND = "native nativesdk"
