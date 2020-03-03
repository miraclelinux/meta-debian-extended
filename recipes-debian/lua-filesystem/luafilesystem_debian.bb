# base recipe: recipes-lua/luafilesystem/luafilesystem_git.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

inherit debian-package
require recipes-debian/sources/lua-filesystem.inc

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d9b7e441d51a96b17511ee3be5a75857"

DEPENDS = "lua"

DEBIAN_UNPACK_DIR = "${WORKDIR}/lua-filesystem-${PV}"

inherit pkgconfig

EXTRA_OEMAKE = 'PREFIX=${D}/usr CROSS_COMPILE=${TARGET_PREFIX} CC="${CC} -fpic" LDFLAGS="${LDFLAGS}"'

# NOTE: this is a Makefile-only piece of software, so we cannot generate much of the
# recipe automatically - you will need to examine the Makefile yourself and ensure
# that the appropriate arguments are passed in.

FILES_${PN} = "${libdir} ${datadir}/lua" 

do_configure () {
	# Specify any needed configure commands here
	:
}

do_compile () {
	# You will almost certainly need to add additional arguments here
	oe_runmake
}

do_install () {
	# This is a guess; additional arguments may be required
	oe_runmake install
}

