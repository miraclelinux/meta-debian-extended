# base recipe: meta/recipes-devtools/apt/apt_1.2.24.bb
# base branch: warrior
# base commit: aab11d8d283fe2d1619eca20eee83bc5a3e230bd

DEPENDS = "virtual/libiconv curl db zlib lz4 perl dpkg gnutls"
RDEPENDS_${PN} = "dpkg bash debianutils lz4 perl"
require apt.inc

require apt-package.inc

apt-manpages += "doc/apt-key.8"

do_install_append() {
    #Write the correct apt-architecture to apt.conf
    APT_CONF=${D}/etc/apt/apt.conf
    echo 'APT::Architecture "${DPKG_ARCH}";' > ${APT_CONF}
}
