# base recipe: meta-openembedded/meta-networking/recipes-support/unbound/unbound_1.9.2.bb
# base branch: master
# base commit: 3221fc4f28b79e2654ae4e82ab8f1c9a222c93ab

SUMMARY = "Unbound is a validating, recursive, and caching DNS resolver"
DESCRIPTION = "Unbound's design is a set of modular components which incorporate \
	features including enhanced security (DNSSEC) validation, Internet Protocol \
	Version 6 (IPv6), and a client resolver library API as an integral part of the \
	architecture"

HOMEPAGE = "https://www.unbound.net/"
SECTION = "net"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5308494bc0590c0cb036afd781d78f06"

inherit debian-package
require recipes-debian/sources/unbound.inc

SRC_URI += "file://0001-contrib-add-yocto-compatible-startup-scripts.patch"

inherit autotools pkgconfig systemd update-rc.d

DEPENDS = "openssl libevent libtool-native bison-native expat"
RDEPENDS_${PN} = "bash openssl-bin daemonize"

EXTRA_OECONF = "--with-libexpat=${STAGING_EXECPREFIXDIR} \
		--with-ssl=${STAGING_EXECPREFIXDIR} \
		libtool=${TARGET_PREFIX}libtool \
"
		

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'largefile', d)}"
PACKAGECONFIG[dnscrypt] = "--enable-dnscrypt, --disable-dnscrypt, libsodium"
PACKAGECONFIG[largefile] = "--enable-largefile,--disable-largefile,,"

inherit useradd
USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} += "--system -d / -M --shell /bin/nologin unbound"

do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${B}/contrib/unbound.service ${D}${systemd_unitdir}/system

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/contrib/unbound.init ${D}${sysconfdir}/init.d/unbound
}

SYSTEMD_SERVICE_${PN} = "${BPN}.service"

INITSCRIPT_NAME = "unbound"
INITSCRIPT_PARAMS = "defaults"
