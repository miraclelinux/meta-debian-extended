# base recipe: poky/meta/recipes-extended/xinetd/xinetd_2.3.15.bb
# base branch: warrior
# base commit: c8c383d958807b991e31d22f612ba2a81a3860a4

inherit debian-package
require recipes-debian/sources/xinetd.inc

SUMMARY = "Socket-based service activation daemon"
HOMEPAGE = "https://github.com/xinetd-org/xinetd"

# xinetd is a BSD-like license
# Apple and Gentoo say BSD here.
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=55c5fdf02cfcca3fc9621b6f2ceae10f"

DEPENDS += "libtirpc"
RDEPENDS_${PN} = "perl lsb perl-module-socket perl-module-io-socket "

DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_QUILT_PATCHES = ""

inherit autotools update-rc.d systemd gettext

SYSTEMD_SERVICE_${PN} = "xinetd.service"

INITSCRIPT_NAME = "xinetd"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OECONF="--disable-nls"

PACKAGECONFIG ??= "tcp-wrappers"
PACKAGECONFIG[tcp-wrappers] = "--with-libwrap,,tcp-wrappers"

CFLAGS += "-I${STAGING_INCDIR}/tirpc"
LDFLAGS += "-ltirpc"

do_configure() {
	# Looks like configure.in is broken, so we are skipping
	# rebuilding configure and are just using the shipped one
	#( cd ${S}; gnu-configize --force )
	cd ${S}
        sh autogen.sh
	oe_runconf --without-libwrap --without-labeled-networking --without-loadavg
}

do_compile() {
	cd ${S}
	oe_runmake
}

do_install() {
	# Same here, the Makefile does some really stupid things,
	# but since we only want two files why not override
	# do_install from autotools and doing it ourselfs?
	install -d "${D}${sbindir}"
	install -d "${D}${sysconfdir}/init.d"
	install -d "${D}${sysconfdir}/xinetd.d"
	install -d "${D}${sysconfdir}/default"
	install -m 644 "${S}/debian/xinetd.conf" "${D}${sysconfdir}"
	install -m 755 "${S}/debian/xinetd.init" "${D}${sysconfdir}/init.d/xinetd"
	install -m 644 "${S}/debian/xinetd.default" "${D}${sysconfdir}/default/xinetd"
	install -m 755 "${S}/xinetd" "${D}${sbindir}"
	install -m 755 "${S}/itox" "${D}${sbindir}"
	install -m 664 ${S}/contrib/xinetd.d/* ${D}${sysconfdir}/xinetd.d

	# Install systemd unit files
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${S}/contrib/xinetd.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@BASE_BINDIR@,${base_bindir},g' \
	       -e 's,@SBINDIR@,${sbindir},g' \
	       ${D}${systemd_unitdir}/system/xinetd.service
}

CONFFILES_${PN} = "${sysconfdir}/xinetd.conf"

