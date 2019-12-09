FILESEXTRAPATHS_prepend := "${THISDIR}/glib-2.0:"
SRC_URI += " \
    file://0001-Do-not-write-bindir-into-pkg-config-files.patch \
"

do_write_config_append_class-nativesdk() {
    sed -e "/^c_args/ s|c_args = \[|c_args = \['$(echo ${TOOLCHAIN_OPTIONS} | sed -e 's/^ *//')', |" \
        -e "/^c_link_args/ s|c_link_args = |c_link_args = \['$(echo ${TOOLCHAIN_OPTIONS} | sed -e 's/^ *//')', |" \
        -e "/^c_link_args/ s|$|\]|" \
        -e "/^cpp_args/ s|cpp_args = \[|cpp_args = \['$(echo ${TOOLCHAIN_OPTIONS} | sed -e 's/^ *//')', |" \
        -e "/^cpp_link_args/ s|cpp_link_args = |cpp_link_args = \['$(echo ${TOOLCHAIN_OPTIONS} | sed -e 's/^ *//')', |" \
        -e "/^cpp_link_args/ s|$|\]|" \
        -i ${WORKDIR}/meson.cross
}
