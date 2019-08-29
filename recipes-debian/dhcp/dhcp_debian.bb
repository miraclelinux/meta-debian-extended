# base recipe: meta/recipes-connectivity/dhcp/dhcp_4.4.1.bb
# base branch: warrior
# base commit: 02f8ed1a1a96ec33b14e63b5ee21cb284361865b

require ${COREBASE}/meta/recipes-connectivity/dhcp/dhcp.inc

inherit debian-package
require recipes-debian/sources/isc-dhcp.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-connectivity/${PN}/${PN}:${COREBASE}/meta/recipes-connectivity/${PN}/files"
SRC_URI += "file://0001-define-macro-_PATH_DHCPD_CONF-and-_PATH_DHCLIENT_CON.patch \
            file://0002-dhclient-dbus.patch \
            file://0003-link-with-lcrypto.patch \
            file://0004-Fix-out-of-tree-builds.patch \
            file://0005-dhcp-client-fix-invoke-dhclient-script-failed-on-Rea.patch \
            file://0006-site.h-enable-gentle-shutdown.patch \
            file://0007-Add-configure-argument-to-make-the-libxml2-dependenc.patch \
            file://0009-remove-dhclient-script-bash-dependency.patch \
            file://0012-dhcp-correct-the-intention-for-xml2-lib-search.patch \
            file://0013-fixup_use_libbind.patch \
            file://0001-master-Added-includes-of-new-BIND9-compatibility-hea.patch \
            file://init-relay file://default-relay \
            file://init-server file://default-server \
            file://dhclient.conf file://dhcpd.conf \
            file://dhclient-systemd-wrapper \
            file://dhclient.service \
            file://dhcpd.service file://dhcrelay.service \
            file://dhcpd6.service \
"

do_debian_patch_prepend() {
    sed -i -e '/system-bind\.patch/ d' ${S}/debian/patches/series
}

LDFLAGS_append = " -pthread"

PACKAGECONFIG ?= ""
PACKAGECONFIG[bind-httpstats] = "--with-libxml2,--without-libxml2,libxml2"
